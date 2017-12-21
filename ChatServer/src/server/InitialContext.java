package server;

class InitialContext {

    public Object lookup(String jndiName) {
        if (jndiName.equalsIgnoreCase("serverController")) {
            return ServerController.getInstance();
        }
        return null;
    }
}
