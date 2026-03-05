package org.botPromo;

import org.botPromo.bot.bd.Connections;
import org.botPromo.bot.model.Produto;
import org.botPromo.bot.services.MercadoLivreService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main() throws Exception {
        /*try {
            Connection conn = Connections.getConexao();
            if(conn != null){
                System.out.println("Conectado com sucesso!");
            }
        }catch(Exception e){
            System.err.println("Erro ao conectar ao banco de dados " + e.getMessage());

        } */
        String urlOriginal = "https://www.mercadolivre.com.br/headset-bluetooth-reduco-de-ruido-do-anc-hifi-com-microfone-cor-branco/p/MLB41427451?pdp_filters=item_id:MLB5329494116#is_advertising=true&searchVariation=MLB41427451&backend_model=search-backend&position=2&search_layout=grid&type=pad&tracking_id=82a87e30-a5f8-4e42-98ef-59e7a90801cc&ad_domain=VQCATCORE_LST&ad_position=2&ad_click_id=MTE0OWFkYjItMGJlNC00NjQzLWE4ZTMtOGY3NzVlNjQwNmY1";
        String idProduto = "MLB3510521362";

        MercadoLivreService.processarComCerteza(idProduto);



    }
}
