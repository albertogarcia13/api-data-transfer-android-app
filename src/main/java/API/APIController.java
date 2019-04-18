package API;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class APIController {
    private int cont = 0;

    @RequestMapping(value = "/getGaste", method = GET)
    public String greeting() throws SQLException {
        DBConnection c = new DBConnection();
        Connection conexion = c.openConnection();
        String resp = c.getGaste(conexion);
        c.closeConnection(conexion);
        return resp;
    }

    @RequestMapping(value = "/addGaste", method = POST)
    public String greeting1(@RequestParam(value = "shop") String shop, @RequestParam(value = "ingress") String ingress, @RequestParam(value = "value") String value) throws SQLException {
        DBConnection c = new DBConnection();
        Connection conexion = c.openConnection();
        c.addGaste(conexion, shop, ingress, value);
        c.closeConnection(conexion);
        return "Correct data add";

    }
}