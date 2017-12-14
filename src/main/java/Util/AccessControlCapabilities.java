package Util;

import Resources.AccessOperation;
import Resources.Capability;

public class AccessControlCapabilities {

    Capability makeKey(String Resource, AccessOperation Operation, int ExpireTime) {
        return null;
    }

    boolean checkPermission(Capability key, String Resource, AccessOperation Operation) {
        return makeKey(Resource, Operation, 0).equals(key);
    }

}
