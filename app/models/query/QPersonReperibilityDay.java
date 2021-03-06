package models.query;

import static com.querydsl.core.types.PathMetadataFactory.*;
import models.PersonReperibilityDay;


import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonReperibilityDay is a Querydsl query type for PersonReperibilityDay
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPersonReperibilityDay extends EntityPathBase<PersonReperibilityDay> {

    private static final long serialVersionUID = 1611858907L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonReperibilityDay personReperibilityDay = new QPersonReperibilityDay("personReperibilityDay");

    public final models.base.query.QBaseModel _super = new models.base.query.QBaseModel(this);

    public final DatePath<org.joda.time.LocalDate> date = createDate("date", org.joda.time.LocalDate.class);

    //inherited
    public final SimplePath<Object> entityId = _super.entityId;

    public final BooleanPath holidayDay = createBoolean("holidayDay");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath persistent = _super.persistent;

    public final QPersonReperibility personReperibility;

    public final QPersonReperibilityType reperibilityType;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QPersonReperibilityDay(String variable) {
        this(PersonReperibilityDay.class, forVariable(variable), INITS);
    }

    public QPersonReperibilityDay(Path<? extends PersonReperibilityDay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonReperibilityDay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonReperibilityDay(PathMetadata metadata, PathInits inits) {
        this(PersonReperibilityDay.class, metadata, inits);
    }

    public QPersonReperibilityDay(Class<? extends PersonReperibilityDay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.personReperibility = inits.isInitialized("personReperibility") ? new QPersonReperibility(forProperty("personReperibility"), inits.get("personReperibility")) : null;
        this.reperibilityType = inits.isInitialized("reperibilityType") ? new QPersonReperibilityType(forProperty("reperibilityType"), inits.get("reperibilityType")) : null;
    }

}

