package geeks.mitransporte.Menu;

class InfoCiudad {

    private String tipo, lugar, descripcion;

    public InfoCiudad(String tipo, String lugar, String descripcion) {
        this.tipo = tipo;
        this.lugar = lugar;
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
