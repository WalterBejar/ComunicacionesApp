package com.pango.comunicaciones;

/**
 * Created by NILRAD on 25/09/2017.
 */

public class Utils {

    public static String baseUrl = "https://app.antapaccay.com.pe/proportal/scom_service/api";
    public static String loginDominio = "anyaccess";
    public static String token = "eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNyc2Etc2hhMjU2IiwidHlwIjoiSldUIn0.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYW50YXBhY2NheSIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMDAwMDA3OTU4MCIsImV4cCI6MTUwNjQ0NzA0MSwiaXNzIjoiaHR0cDovL2lzc3Vlci5jb20iLCJhdWQiOiJodHRwOi8vbXlzaXRlLmNvbSJ9.flQkRfCw9G5esmH73EuxwyJOmbTvcm0jPCE1IHyjrwO2m5d8a9qsEVnnNqCZPZ7dEx1Pnaxmh0sb1S2RVFk73pb2V2BuNR2PGYTtzKeUL75p8gQcSqz5l7vukoh-kfev19reqvx3Ug_aFAv2fpaN-SMCVX2GfrQFYrZ5EgGhE8WL2HTZeekg69QycdRi6JclLoLY2i9ZI1j9p2dXzd7fVBOONl_hLj-iP6wamd3jIb81LPhk4Hu1NBTPtpIK30TQaB_oZzrNkEhDGKD-R4zqSaA0_ieA8juSlMO5v1OGhlkgv4hYmZ9hPXUDEi73-0X8u7hAEVzcHJpMsSBBtbAN6A";
    public static Boolean esAdmin = false;

    static String getUrlForReservaTicketsLogin(String username, String password) {
        return baseUrl + "/membership/authenticate?username=" + username + "&password=" + password + "&domain=" + Utils.loginDominio;
    }

    static String getUrlForCheckIfAdmin() {
        return baseUrl + "/persona/Get_Usuario";
    }
    static String getUrlForReservaTicketTerminales() {
        return baseUrl + "/maestro/get_terminal";
    }
}
