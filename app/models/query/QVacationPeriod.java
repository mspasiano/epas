package models.query;

import static com.querydsl.core.types.PathMetadataFactory.*;
import models.VacationPeriod;


import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVacationPeriod is a Querydsl query type for VacationPeriod
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVacationPeriod extends EntityPathBase<VacationPeriod> {

    private static final long serialVersionUID = -1413922014L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVacationPeriod vacationPeriod = new QVacationPeriod("vacationPeriod");

    public final models.base.query.QPropertyInPeriod _super = new models.base.query.QPropertyInPeriod(this);

    //inherited
    public final DatePath<org.joda.time.LocalDate> beginDate = _super.beginDate;

    public final QContract contract;

    //inherited
    public final DatePath<org.joda.time.LocalDate> endDate = _super.endDate;

    //inherited
    public final SimplePath<Object> entityId = _super.entityId;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath label = createString("label");

    public final SimplePath<models.base.IPropertiesInPeriodOwner> owner = createSimple("owner", models.base.IPropertiesInPeriodOwner.class);

    //inherited
    public final BooleanPath persistent = _super.persistent;

    public final SimplePath<Object> type = createSimple("type", Object.class);

    public final EnumPath<models.enumerate.VacationCode> vacationCode = createEnum("vacationCode", models.enumerate.VacationCode.class);

    public final SimplePath<Object> value = createSimple("value", Object.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QVacationPeriod(String variable) {
        this(VacationPeriod.class, forVariable(variable), INITS);
    }

    public QVacationPeriod(Path<? extends VacationPeriod> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVacationPeriod(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVacationPeriod(PathMetadata metadata, PathInits inits) {
        this(VacationPeriod.class, metadata, inits);
    }

    public QVacationPeriod(Class<? extends VacationPeriod> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contract = inits.isInitialized("contract") ? new QContract(forProperty("contract"), inits.get("contract")) : null;
    }

}

