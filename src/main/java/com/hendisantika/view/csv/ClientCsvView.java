package com.hendisantika.view.csv;

import com.hendisantika.model.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-invoice-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 15/09/21
 * Time: 05.22
 */
@Component("list.csv")
public class ClientCsvView extends AbstractView {

    public ClientCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        final String filename = "clients.csv";
        res.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        res.setContentType(getContentType());

        Page<Client> clients = (Page<Client>) model.get("clients");
        ICsvBeanWriter beanWriter = new CsvBeanWriter(res.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        // EXACT names of the properties of the objects to write,
        // in this case clients
        String[] headers = {"id", "name", "surname", "email", "createdAt"};
        beanWriter.writeHeader(headers);

        for (Client client : clients) {
            beanWriter.write(client, headers);
        }
        beanWriter.close();
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }
}
