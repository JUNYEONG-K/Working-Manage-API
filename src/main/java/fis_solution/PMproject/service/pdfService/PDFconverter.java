package fis_solution.PMproject.service.pdfService;

import fis_solution.PMproject.domain.record.Cases;
import fis_solution.PMproject.domain.record.Files;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

// 현승구 08/26

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/*
    image to PDF
    변환기
    api ?
    createPDFFile(String 이미지있는 폴더 경로, String 만들 pdf 이름);
    copyfile(String Dest, String source);
    createuploaddir(label정보, case정보);
 */


public class PDFconverter {
    //PDF 문서 객체 준비

    // 해당 폴더에 있는 이미지 파일를 하나의 pdf로 변환
    public String createPDFFile(String imageDir, String PDFFileName) throws IOException {
        PDDocument document = new PDDocument();
        String pdf_path;
        try {
            File dir = new File(imageDir);
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".tiff");
                }
            });
            System.out.println("documentpath = " + imageDir);
            int img_num = 0;
            while (img_num < files.length) {
                if(files[img_num] == null){
                    continue;
                }
                if(files[img_num].isDirectory()){
                    continue;
                }
                System.out.println("img_num = " + img_num);
                System.out.println("files = " + files[img_num].getName());
                addImage(files[img_num].getAbsolutePath(), document);
                img_num++;
            }
            System.out.println("빠져나온 경우");
            // pdf 파일이름 8자리 숫자 0~!
            document.save(dir.getAbsolutePath() + File.separator + PDFFileName + ".pdf");
            pdf_path = dir.getAbsolutePath() + File.separator + PDFFileName + ".pdf";
            return pdf_path;
        } catch (Exception e) {
                System.out.println(e);
                return null;
        }
    }

    //이미지 경로를 주었을 때 넘겨준 pdf 객체에 이미지를 추가한다.
    public void addImage(String imgpath, PDDocument PDFlabel) throws IOException {
        File sourceimage = new File(imgpath);
        InputStream inputStream = new FileInputStream(sourceimage);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        // image경로를 찾아서 inputstream에 저장하고 inputstream에 저장된 이미지를 꺼내서 버퍼이미지에 저장
        // inputstream들을 사용하기 위해서는 exception이 반드시 필요하다.
        float width = bufferedImage.getWidth();
        float height = bufferedImage.getHeight();
        // 이미지 크기 저장

        PDPage page = new PDPage(new PDRectangle(width, height));
        //빈 페이지를 생성 pdf 크기는 이미지 크기에 맞춰서
        PDFlabel.addPage(page);
        //빈 페이지를 pdf파일 (PDFlabel 변수)에 추가

        PDImageXObject pdImage = PDImageXObject.createFromFile(sourceimage.getAbsolutePath(), PDFlabel);
        //source이미지를 pdf형식에 맞게 image로 불러와서 pdimagex객체를 생성한다 경로를 바탕으로
        PDPageContentStream contentStream = new PDPageContentStream(PDFlabel, page);
        //pdf image를 pdfcontent steam에 넣는다.
        contentStream.drawImage(pdImage, 0, 0, width, height);
        // 넣어진로 그린다.

        if (contentStream != null) {
            contentStream.close();
        }

        if (inputStream != null) {
            contentStream.close();
        }
    }

//    해당 폴더에 있는 파일의 갯수 (폴더가 아닌) 반환
//    public int showFileCount(String path) throws Exception {
//        File dir = new File(path);
//        File[] files = dir.listFiles();
//        return files.length;
//    }

    public String createuploaddir(Files label, Cases cases){
        //28자리 철 dir 이름
        String o_code = label.getOffice().getO_code();

        String labeldirname =
        label.getF_inheritance() // 처리과 기관코드
        + label.getF_unitcode()   //  단위업무 코드
        + label.getF_inherpyear() // 생산년도
        + label.getF_inherlabelcode() // 기록물철등록일련번호
        + label.getF_volumeamount();  // 권호수

        //19 자리 건 dir 이름
        String casedirname =
        cases.getC_pdate() //생산등록일자
        + cases.getC_pnum() //생산(접수)등록번호
        + cases.getC_attachnum(); // 기록물 분리등록번호

        File folder = new File(System.getProperty("user.home") + File.separator + o_code);
        if(!folder.exists()){
            folder.mkdir();
        }

        File datafolder = new File(System.getProperty("user.home") + File.separator + o_code + File.separator + "DATA");
        if(!datafolder.exists()){
            datafolder.mkdir();
        }

        File imagefolder = new File(System.getProperty("user.home") + File.separator + o_code + File.separator + "IMAGE");
        if(!imagefolder.exists()){
            imagefolder.mkdir();
        }

        File labelFolder = new File(System.getProperty("user.home") + File.separator + o_code + File.separator + "IMAGE" + File.separator + labeldirname);
        if(!labelFolder.exists()){
            labelFolder.mkdir();
        }
        File caseFolder = new File(System.getProperty("user.home") + File.separator + o_code + File.separator + "IMAGE" + File.separator + labeldirname + File.separator + casedirname);
        if(caseFolder.exists()){
            caseFolder.mkdir();
        }

        String path = System.getProperty("user.home") + File.separator + o_code + File.separator + "IMAGE" + File.separator + labeldirname + File.separator + casedirname;
        return path;
    }

    public void copyFile(String dest, String source){
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //현승구 test
    // 나중에 file.seperator 로 전환 /를
    public static void main(String[] args) throws IOException {
//        File folder = new File("/Users/hyeonseung-gu/" + "dfsdfs");
//        if(!folder.exists()){
//            folder.mkdir();
//        }
//        File labelFolder = new File("/Users/hyeonseung-gu/" + "dfsdfs" + "/" + "label");
//        if(!labelFolder.exists()){
//            labelFolder.mkdir();
//        }
//        File caseFolder = new File(File.separator + "Users" + File.separator + "hyeonseung-gu" + File.separator + "dfsdfs" + File.separator  + "label" + File.separator + "case");
//        if(!caseFolder.exists()){
//            caseFolder.mkdir();
//        }
//
//        PDFconverter a = new PDFconverter();
//        a.createPDFFile("/Users/hyeonseung-gu/dfsdfs/label/case", "sibal");
//
//        a.copyFile("/Users/hyeonseung-gu/dfsdfs/label/copy.pdf", "/Users/hyeonseung-gu/dfsdfs/label/case/sibal.pdf");
        System.out.println("System.getProperty(\"user.home\") = " + System.getProperty("user.home"));
    }

}
