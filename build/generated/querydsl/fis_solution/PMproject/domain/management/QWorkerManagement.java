package fis_solution.PMproject.domain.management;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkerManagement is a Querydsl query type for WorkerManagement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkerManagement extends EntityPathBase<WorkerManagement> {

    private static final long serialVersionUID = -1257998662L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkerManagement workerManagement = new QWorkerManagement("workerManagement");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> m_amount = createNumber("m_amount", Long.class);

    public final NumberPath<Integer> m_attend = createNumber("m_attend", Integer.class);

    public final StringPath m_date = createString("m_date");

    public final StringPath m_etc = createString("m_etc");

    public final NumberPath<Integer> m_late = createNumber("m_late", Integer.class);

    public final NumberPath<Integer> m_leave = createNumber("m_leave", Integer.class);

    public final NumberPath<Integer> m_night = createNumber("m_night", Integer.class);

    public final NumberPath<Integer> m_out = createNumber("m_out", Integer.class);

    public final NumberPath<Integer> m_process = createNumber("m_process", Integer.class);

    public final QWorker worker;

    public QWorkerManagement(String variable) {
        this(WorkerManagement.class, forVariable(variable), INITS);
    }

    public QWorkerManagement(Path<? extends WorkerManagement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkerManagement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkerManagement(PathMetadata metadata, PathInits inits) {
        this(WorkerManagement.class, metadata, inits);
    }

    public QWorkerManagement(Class<? extends WorkerManagement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.worker = inits.isInitialized("worker") ? new QWorker(forProperty("worker")) : null;
    }

}

