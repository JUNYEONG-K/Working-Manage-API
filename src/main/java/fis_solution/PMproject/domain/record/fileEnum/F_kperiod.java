package fis_solution.PMproject.domain.record.fileEnum;

//철보존기간
//(01-1년, 03-3년, 05-5년, 10-10년, 20-20년, 25-30년, 30-준영구, 40-영구)
public enum F_kperiod {
    NONE(""),
    YEAR1("01"),
    YEAR3("03"),
    YEAR5("05"),
    YEAR10("10"),
    YEAR20("20"),
    YEAR30("25"),
    SEMI("30"),
    PERMANENT("40");

    private String kperiod;

    F_kperiod(String kperiod) {
        this.kperiod = kperiod;
    }

    public String getKperiod() {
        return kperiod;
    }
}
