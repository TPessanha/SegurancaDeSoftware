package Util;

import Resources.AccessOperation;
import Resources.Account;
import Resources.Capability;
import Resources.Role;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import static Util.Constants.CAPABILITY_EXPIRE_TIME;

public class AccessControlCapabilities {

    static protected Capability makeKey(String Resource, AccessOperation Operation, long ExpireTime) {
        return new Capability(Resource, Operation, ExpireTime);
    }

    static protected Capability makeKey(String Resource, AccessOperation Operation) {
        return new Capability(Resource, Operation);
    }

    static protected Capability makeKey(String Resource, AccessOperation Operation, String Account) {
        return new Capability(Resource, Operation, Account);
    }

    static protected Capability makeKey(String Resource, AccessOperation Operation, long ExpireTime, String Account) {
        return new Capability(Resource, Operation, ExpireTime, Account);
    }

    static protected String genToken(Capability capability) throws Exception {
        String token = capability.getIdentifier();
        token += capability.getOperation().name() + ".";

        if (capability.getExpirationDate() != 0)
            token += String.valueOf(capability.getExpirationDate());
        if (capability.getLinkedAccount() != null)
            token += capability.getLinkedAccount();

        token = CryptoUtil.encryptAES(token);

        if (capability.getLinkedAccount() != null)
            token = token + "." + capability.getLinkedAccount();
        return token + "." + capability.getExpirationDate();
    }

    static public boolean checkPermission(List<String> capabilities, String Resource, AccessOperation Operation) throws Exception {
        Date now = new Date();
        Date expirationCapa;
        boolean auth = false;

        Iterator<String> it = capabilities.iterator();
        while (it.hasNext()) {
            String tokenKey = it.next();
            String[] fields = tokenKey.split(Pattern.quote("."));
            long exTime = 0;
            String acc = null;
            //1 so expiration date

            exTime = Long.parseLong(fields[1]);

            expirationCapa = new Date(exTime);
            if (now.after(expirationCapa) && exTime != 0) {
                it.remove();
                continue;
            }

            if (genToken(makeKey(Resource, Operation, exTime, acc)).equals(tokenKey))
                auth = true;
        }
        return auth;
    }

    static public boolean checkPermission(Account account, String Resource, AccessOperation Operation) throws Exception {
        List<String> capabilities = account.getCapabilities();
        Date now = new Date();
        Date expirationCapa;
        boolean auth = false;

        Iterator<String> it = capabilities.iterator();
        while (it.hasNext()) {
            String tokenKey = it.next();
            int countDots = countDots(tokenKey);
            String[] fields = tokenKey.split(Pattern.quote("."));
            long exTime = 0;
            String acc = null;
            switch (countDots) {
                case 1:
                    exTime = Long.parseLong(fields[1]);
                    break;
                case 2:
                    exTime = Long.parseLong(fields[2]);
                    acc = fields[1];
                    break;
            }
            expirationCapa = new Date(exTime);
            if (acc != null && !account.getUsername().equalsIgnoreCase(acc)) {
                it.remove();
                continue;
            }
            if (now.after(expirationCapa) && exTime != 0) {
                it.remove();
                continue;
            }

            if (genToken(makeKey(Resource, Operation, exTime, acc)).equals(tokenKey))
                auth = true;
        }
        return auth;
    }

    static private int countDots(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '.')
                count++;
        }
        return count;
    }


    static public void addCapabilities(HttpSession session) throws Exception {
        Account account = Authenticator.get_account(session.getAttribute("USER").toString());
        List<String> capabilities = new ArrayList<>();
        Date now = new Date();
        Date end = new Date(now.getTime() + 1000 * CAPABILITY_EXPIRE_TIME);
        if (account.getRole() == Role.ADMIN) {
            capabilities.add(genToken(new Capability("WebApp.Users", AccessOperation.CREATE, end.getTime())));
            capabilities.add(genToken(new Capability("WebApp.Users", AccessOperation.DELETE, end.getTime())));
        }

        //Eveyones permissions
        capabilities.add(genToken(new Capability("WebApp.Users." + account.getUsername(), AccessOperation.MODIFY, end.getTime())));

        session.setAttribute("CAPABILITIES", capabilities);
    }

}
