package com.pango.comunicaciones;

/**
 * Created by NILRAD on 25/09/2017.
 */

public class Utils {

    public static String baseUrl = "https://app.antapaccay.com.pe/proportal/scom_service/api";
    public static String loginDominio = "anyaccess";
    public static String token = "eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNyc2Etc2hhMjU2IiwidHlwIjoiSldUIn0.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYW50YXBhY2NheSIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMDAwMDA3OTU4MCIsImV4cCI6MTUwNjM3MjI1NywiaXNzIjoiaHR0cDovL2lzc3Vlci5jb20iLCJhdWQiOiJodHRwOi8vbXlzaXRlLmNvbSJ9.Y0aZurux6cD0fmlZvQTqZUPvEFBb_N7fc0U9_GCWXHz6hCgOgmXJW3xfAhJTpCIB1cIV9S2fb5h0rXNI8sc0LOj8Zer8twrsz0-C0SvfkpTi_74sD1DIz6OWVWtYiu4PipZbieNH683awkzR3n_D1mI2ChRrTVgvS1jkKoNAH-qgiGJXq85EY9ijSWydpX7sK4iEhseYAj-frDBbuegkkFRBDv3XzPYU6UeEFVsRUoOPP_WBTwRcV0L_5JkHzAsLIArCKIEyp3CCQgAiBKVmfx1HKl8WENsAHUfLAUlJttLUwKV7j3UqYyw9jszm3IhSqs6b20UVgTb4A17ykDGjKQ";
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
