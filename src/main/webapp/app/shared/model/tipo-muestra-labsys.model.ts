export interface ITipoMuestraLabsys {
    id?: number;
    descripcion?: string;
    procedimientoId?: number;
    recipienteId?: number;
}

export class TipoMuestraLabsys implements ITipoMuestraLabsys {
    constructor(public id?: number, public descripcion?: string, public procedimientoId?: number, public recipienteId?: number) {}
}
