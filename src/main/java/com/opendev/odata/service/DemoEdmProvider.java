package com.opendev.odata.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;
import org.springframework.stereotype.Component;

import com.opendev.odata.util.Constants;

/**
 * @author Subash
 * @since 2/28/2021
 */
@Component
public class DemoEdmProvider extends CsdlAbstractEdmProvider {

	/**
	 * This method is called for one of the EntityTypes that are configured in the
	 * Schema
	 */
	@Override
	public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
		if (entityTypeName.equals(Constants.ET_PRODUCT_FQN)) {

			// create EntityType properties
			CsdlProperty id = new CsdlProperty().setName("ID")
					.setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
			CsdlProperty name = new CsdlProperty().setName("Name")
					.setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
			CsdlProperty description = new CsdlProperty().setName("Description")
					.setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

			// create CsdlPropertyRef for Key element
			CsdlPropertyRef propertyRef = new CsdlPropertyRef();
			propertyRef.setName("ID");

			// configure EntityType
			CsdlEntityType entityType = new CsdlEntityType();
			entityType.setName(Constants.ET_PRODUCT_NAME);
			entityType.setProperties(Arrays.asList(id, name, description));
			entityType.setKey(Collections.singletonList(propertyRef));

			return entityType;
		}

		return null;
	}

	@Override
	public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
		if (entityContainer.equals(Constants.CONTAINER) && entitySetName.equals(Constants.ES_PRODUCTS_NAME)) {
			CsdlEntitySet entitySet = new CsdlEntitySet();
			entitySet.setName(Constants.ES_PRODUCTS_NAME);
			entitySet.setType(Constants.ET_PRODUCT_FQN);

			return entitySet;
		}

		return null;
	}

	/**
	 * This method is invoked when displaying the Service Document at e.g.
	 * http://localhost:8080/OData/V1.0
	 */
	@Override
	public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
		if (entityContainerName == null || entityContainerName.equals(Constants.CONTAINER)) {
			CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
			entityContainerInfo.setContainerName(Constants.CONTAINER);
			return entityContainerInfo;
		}
		return null;
	}

	@Override
	public List<CsdlSchema> getSchemas() throws ODataException {
		// create Schema
		CsdlSchema schema = new CsdlSchema();
		schema.setNamespace(Constants.NAMESPACE);

		// add EntityTypes
		List<CsdlEntityType> entityTypes = new ArrayList<>();
		entityTypes.add(getEntityType(Constants.ET_PRODUCT_FQN));
		schema.setEntityTypes(entityTypes);

		// add EntityContainer
		schema.setEntityContainer(getEntityContainer());

		// finally
		List<CsdlSchema> schemas = new ArrayList<>();
		schemas.add(schema);

		return schemas;
	}

	@Override
	public CsdlEntityContainer getEntityContainer() throws ODataException {
		// create EntitySets
		List<CsdlEntitySet> entitySets = new ArrayList<>();
		entitySets.add(getEntitySet(Constants.CONTAINER, Constants.ES_PRODUCTS_NAME));

		// create EntityContainer
		CsdlEntityContainer entityContainer = new CsdlEntityContainer();
		entityContainer.setName(Constants.CONTAINER_NAME);
		entityContainer.setEntitySets(entitySets);

		return entityContainer;
	}

}
