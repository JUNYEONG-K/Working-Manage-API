package fis_solution.PMproject.domain.management;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorker is a Querydsl query type for Worker
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorker extends EntityPathBase<Worker> {

    private static final long serialVersionUID = -10759401L;

    public static final QWorker worker = new QWorker("worker");

    public final EnumPath<Authority> authority = createEnum("authority", Authority.class);

    public final ListPath<fis_solution.PMproject.domain.record.Cases, fis_solution.PMproject.domain.record.QCases> caseList_check = this.<fis_solution.PMproject.domain.record.Cases, fis_solution.PMproject.domain.record.QCases>createList("caseList_check", fis_solution.PMproject.domain.record.Cases.class, fis_solution.PMproject.domain.record.QCases.class, PathInits.DIRECT2);

    public final ListPath<fis_solution.PMproject.domain.record.Cases, fis_solution.PMproject.domain.record.QCases> caseList_put = this.<fis_solution.PMproject.domain.record.Cases, fis_solution.PMproject.domain.record.QCases>createList("caseList_put", fis_solution.PMproject.domain.record.Cases.class, fis_solution.PMproject.domain.record.QCases.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath session_id = createString("session_id");

    public final StringPath w_account = createString("w_account");

    public final StringPath w_address = createString("w_address");

    public final StringPath w_bank = createString("w_bank");

    public final StringPath w_edate = createString("w_edate");

    public final StringPath w_name = createString("w_name");

    public final StringPath w_sdate = createString("w_sdate");

    public final StringPath w_ssn = createString("w_ssn");

    public final StringPath w_tel = createString("w_tel");

    public final ListPath<WorkerManagement, QWorkerManagement> workerManagements = this.<WorkerManagement, QWorkerManagement>createList("workerManagements", WorkerManagement.class, QWorkerManagement.class, PathInits.DIRECT2);

    public QWorker(String variable) {
        super(Worker.class, forVariable(variable));
    }

    public QWorker(Path<? extends Worker> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorker(PathMetadata metadata) {
        super(Worker.class, metadata);
    }

}

