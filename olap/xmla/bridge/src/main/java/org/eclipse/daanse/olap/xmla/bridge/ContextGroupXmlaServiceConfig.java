package org.eclipse.daanse.olap.xmla.bridge;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition()
public interface ContextGroupXmlaServiceConfig {

	
	// TODO:  do not use configs for i18n in this way
    @AttributeDefinition(name = "%DBSCHEMA_CATALOGS", required = false)
    default String dbSchemaCatalogsDescription() {
        return null;
    }

    @AttributeDefinition(name = "%DISCOVER_DATASOURCES", required = false)
    default String discoverDataSourcesDescription() {
        return null;
    }

    @AttributeDefinition(name = "%DISCOVER_ENUMERATORS", required = false)
    default String discoverEnumeratorsDescription() {
        return null;
    }

    @AttributeDefinition(name = "%DISCOVER_KEYWORDS", required = false)
    default String discoverKeywordsDescription() {
        return null;
    }

    @AttributeDefinition(name = "%DISCOVER_LITERALS", required = false)
    default String discoverLiteralsDescription() {
        return null;
    }

    @AttributeDefinition(name = "%DISCOVER_PROPERTIES", required = false)
    default String discoverPropertiesDescription() {
        return null;
    }

    @AttributeDefinition(name = "%DISCOVER_SCHEMA_ROWSETS", required = false)
    default String discoverSchemaRowSetsDescription() {
        return null;
    }

    @AttributeDefinition(name = "%DISCOVER_XML_METADATA", required = false)
    default String discoverXmlMetadataDescription() {
        return null;
    }

    @AttributeDefinition(name = "%AccessEnum", required = false)
    default String accessEnumDescription() {
        return null;
    }

    @AttributeDefinition(name = "%AccessEnum.Read", required = false)
    default String accessEnumReadDescription() {
        return null;
    }

    @AttributeDefinition(name = "%AccessEnum.Write", required = false)
    default String accessEnumWriteDescription() {
        return null;
    }

    @AttributeDefinition(name = "%AccessEnum.ReadWrite", required = false)
    default String accessEnumReadWriteDescription() {
        return null;
    }


    @AttributeDefinition(name = "%AuthenticationModeEnum", required = false)
    default String authenticationModeEnumDescription() {
        return null;
    }
    @AttributeDefinition(name = "%AuthenticationModeEnum.Unauthenticated", required = false)
    default String authenticationModeEnumUnauthenticatedDescription() {
        return null;
    }
    @AttributeDefinition(name = "%AuthenticationModeEnum.Authenticated", required = false)
    default String authenticationModeEnumAuthenticatedDescription() {
        return null;
    }
    @AttributeDefinition(name = "%AuthenticationModeEnum.Integrated", required = false)
    default String authenticationModeEnumIntegratedDescription() {
        return null;
    }

    @AttributeDefinition(name = "%ProviderTypeEnum", required = false)
    default String providerTypeEnumDescription() {
        return null;
    }
    @AttributeDefinition(name = "%ProviderTypeEnum.TDP", required = false)
    default String providerTypeEnumTDPDescription() {
        return null;
    }
    @AttributeDefinition(name = "%ProviderTypeEnum.MDP", required = false)
    default String providerTypeEnumMDPDescription() {
        return null;
    }
    @AttributeDefinition(name = "%ProviderTypeEnum.DMP", required = false)
    default String providerTypeEnumDMPDescription() {
        return null;
    }


    @AttributeDefinition(name = "%TreeOpEnum", required = false)
    default String treeOpEnumDescription() {
        return null;
    }
    @AttributeDefinition(name = "%TreeOpEnum.MDTREEOP_CHILDREN", required = false)
    default String treeOpEnumMdTreeOpChildrenDescription() {
        return null;
    }
    @AttributeDefinition(name = "%TreeOpEnum.MDTREEOP_SIBLINGS", required = false)
    default String treeOpEnumMdTreeOpSiblingsDescription() {
        return null;
    }
    @AttributeDefinition(name = "%TreeOpEnum.MDTREEOP_PARENT", required = false)
    default String treeOpEnumMdTreeOpParentDescription() {
        return null;
    }
    @AttributeDefinition(name = "%TreeOpEnum.MDTREEOP_SELF", required = false)
    default String treeOpEnumMdTreeOpSelfDescription() {
        return null;
    }
    @AttributeDefinition(name = "%TreeOpEnum.MDTREEOP_DESCENDANTS", required = false)
    default String treeOpEnumMdTreeOpDescendantsDescription() {
        return null;
    }
    @AttributeDefinition(name = "%TreeOpEnum.MDTREEOP_ANCESTORS", required = false)
    default String treeOpEnumMdTreeOpAncestorsDescription() {
        return null;
    }

}
