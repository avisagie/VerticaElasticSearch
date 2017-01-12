/*
 * Copyright (c) 2014 - 2015 Hewlett Packard Enterprise Development LP
 *
 * Description: Support code for UDx subsystem
 *
 * Create Date: Fri Aug 19 15:51:20 2016
 */
/* Build-time identification for VerticaSDKJava */

package com.vertica.sdk;
public class BuildInfo {
    public static final String VERTICA_BUILD_ID_Brand_Name       = "Vertica Analytic Database";
    public static final String VERTICA_BUILD_ID_Brand_Version    = "v8.0.0-0";
    public static final String VERTICA_BUILD_ID_SDK_Version      = "8.0.0";
    public static final String VERTICA_BUILD_ID_Codename         = "Frontloader";
    public static final String VERTICA_BUILD_ID_Date             = "Fri Aug 19 15:51:20 2016";
    public static final String VERTICA_BUILD_ID_Machine          = "build-centos6";
    public static final String VERTICA_BUILD_ID_Branch           = "tag";
    public static final String VERTICA_BUILD_ID_Revision         = "releases/VER_8_0_RELEASE_BUILD_0_0_20160819";
    public static final String VERTICA_BUILD_ID_Checksum         = "0ca6ccab63428453ecd4fd930e92c877";

    public static VerticaBuildInfo get_vertica_build_info() {
        VerticaBuildInfo vbi = new VerticaBuildInfo();
        vbi.brand_name      = BuildInfo.VERTICA_BUILD_ID_Brand_Name;
        vbi.brand_version   = BuildInfo.VERTICA_BUILD_ID_Brand_Version;
            vbi.sdk_version     = BuildInfo.VERTICA_BUILD_ID_SDK_Version;
        vbi.codename        = BuildInfo.VERTICA_BUILD_ID_Codename;
        vbi.build_date      = BuildInfo.VERTICA_BUILD_ID_Date;
        vbi.build_machine   = BuildInfo.VERTICA_BUILD_ID_Machine;
        vbi.branch          = BuildInfo.VERTICA_BUILD_ID_Branch;
        vbi.revision        = BuildInfo.VERTICA_BUILD_ID_Revision;
        vbi.checksum        = BuildInfo.VERTICA_BUILD_ID_Checksum;
        return vbi;
    }
}

/* end of this file */
