package models.query;

import static com.querydsl.core.types.PathMetadataFactory.*;
import models.Zone;


import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZone is a Querydsl query type for Zone
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QZone extends EntityPathBase<Zone> {

    private static final long serialVersionUID = 1339775184L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZone zone = new QZone("zone");

    public final models.base.query.QBaseModel _super = new models.base.query.QBaseModel(this);

    public final QBadgeReader badgeReader;

    public final StringPath description = createString("description");

    //inherited
    public final SimplePath<Object> entityId = _super.entityId;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath persistent = _super.persistent;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final ListPath<models.ZoneToZones, QZoneToZones> zoneLinkedAsMaster = this.<models.ZoneToZones, QZoneToZones>createList("zoneLinkedAsMaster", models.ZoneToZones.class, QZoneToZones.class, PathInits.DIRECT2);

    public final ListPath<models.ZoneToZones, QZoneToZones> zoneLinkedAsSlave = this.<models.ZoneToZones, QZoneToZones>createList("zoneLinkedAsSlave", models.ZoneToZones.class, QZoneToZones.class, PathInits.DIRECT2);

    public QZone(String variable) {
        this(Zone.class, forVariable(variable), INITS);
    }

    public QZone(Path<? extends Zone> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QZone(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QZone(PathMetadata metadata, PathInits inits) {
        this(Zone.class, metadata, inits);
    }

    public QZone(Class<? extends Zone> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.badgeReader = inits.isInitialized("badgeReader") ? new QBadgeReader(forProperty("badgeReader"), inits.get("badgeReader")) : null;
    }

}

