package app.controllers.reports;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;

@Controller
public class PdfController {

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/openPdf")
    public ResponseEntity<InputStreamResource> openPdf(
            @RequestParam("stcode") String stcode   // ✅ Accept stcode from URL
    ) {
        try {
            // You can log or use the state code for further logic
            System.out.println("State code received: " + stcode);

            // If you want to serve different PDFs for each state:
            // Example: /WEB-INF/reference/UserManual_1.pdf, UserManual_2.pdf, etc.
            String pdfFileName = "UserManual_" + stcode + ".pdf";
            String pdfPath = "/WEB-INF/reference/" + pdfFileName;

            // Try loading state-specific PDF
            InputStream pdfStream = servletContext.getResourceAsStream(pdfPath);

            // If not found, fallback to the default manual
            if (pdfStream == null) {
                pdfStream = servletContext.getResourceAsStream("/WEB-INF/reference/UserManual.pdf");
                if (pdfStream == null) {
                    return ResponseEntity.notFound().build();
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + pdfFileName)
                    .body(new InputStreamResource(pdfStream));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}


