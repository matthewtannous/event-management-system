package com.mat.frontend.session;

public class SceneData {
//    // Can be used for anything
//    private static Long id;

    // Any data (should be response dto or String)
    private static Object data;

    public static void setSceneData(Object data) {
//        SceneData.id = id;
        SceneData.data = data;
    }

//    public static Long getId() {
//        return id;
//    }

    // Should be cast to specific Data type from caller
    public static Object getData() {
        return data;
    }

    public static void clear() {
//        SceneData.id = null;
        SceneData.data = null;
    }
}
