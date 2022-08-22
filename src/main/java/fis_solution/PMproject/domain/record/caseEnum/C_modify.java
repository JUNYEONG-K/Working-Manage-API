package fis_solution.PMproject.domain.record.caseEnum;

//수정여부 1 (0.해당없음, 1.수정함)
public enum C_modify {
    NOTHING("0"),
    MODIFY("1");

    private String modify;

    C_modify(String modify) {
        this.modify = modify;
    }

    public String getModify() {
        return modify;
    }
}
