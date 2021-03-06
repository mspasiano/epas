package models.absences.query;

import static com.querydsl.core.types.PathMetadataFactory.*;
import models.absences.JustifiedType;


import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJustifiedType is a Querydsl query type for JustifiedType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QJustifiedType extends EntityPathBase<JustifiedType> {

    private static final long serialVersionUID = -1479050043L;

    public static final QJustifiedType justifiedType = new QJustifiedType("justifiedType");

    public final models.base.query.QBaseModel _super = new models.base.query.QBaseModel(this);

    public final ListPath<models.absences.Absence, QAbsence> absences = this.<models.absences.Absence, QAbsence>createList("absences", models.absences.Absence.class, QAbsence.class, PathInits.DIRECT2);

    public final SetPath<models.absences.AbsenceType, QAbsenceType> absenceTypes = this.<models.absences.AbsenceType, QAbsenceType>createSet("absenceTypes", models.absences.AbsenceType.class, QAbsenceType.class, PathInits.DIRECT2);

    //inherited
    public final SimplePath<Object> entityId = _super.entityId;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final EnumPath<JustifiedType.JustifiedTypeName> name = createEnum("name", JustifiedType.JustifiedTypeName.class);

    //inherited
    public final BooleanPath persistent = _super.persistent;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QJustifiedType(String variable) {
        super(JustifiedType.class, forVariable(variable));
    }

    public QJustifiedType(Path<? extends JustifiedType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJustifiedType(PathMetadata metadata) {
        super(JustifiedType.class, metadata);
    }

}

