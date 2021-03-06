package models.query;

import static com.querydsl.core.types.PathMetadataFactory.*;
import models.PersonConfiguration;


import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonConfiguration is a Querydsl query type for PersonConfiguration
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPersonConfiguration extends EntityPathBase<PersonConfiguration> {

    private static final long serialVersionUID = -1843971587L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonConfiguration personConfiguration = new QPersonConfiguration("personConfiguration");

    public final models.base.query.QPropertyInPeriod _super = new models.base.query.QPropertyInPeriod(this);

    //inherited
    public final DatePath<org.joda.time.LocalDate> beginDate = _super.beginDate;

    //inherited
    public final DatePath<org.joda.time.LocalDate> endDate = _super.endDate;

    //inherited
    public final SimplePath<Object> entityId = _super.entityId;

    public final EnumPath<manager.configurations.EpasParam> epasParam = createEnum("epasParam", manager.configurations.EpasParam.class);

    public final StringPath fieldValue = createString("fieldValue");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath persistent = _super.persistent;

    public final QPerson person;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QPersonConfiguration(String variable) {
        this(PersonConfiguration.class, forVariable(variable), INITS);
    }

    public QPersonConfiguration(Path<? extends PersonConfiguration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonConfiguration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonConfiguration(PathMetadata metadata, PathInits inits) {
        this(PersonConfiguration.class, metadata, inits);
    }

    public QPersonConfiguration(Class<? extends PersonConfiguration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.person = inits.isInitialized("person") ? new QPerson(forProperty("person"), inits.get("person")) : null;
    }

}

