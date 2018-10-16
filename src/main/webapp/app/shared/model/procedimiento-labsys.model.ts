export interface IProcedimientoLabsys {
    id?: number;
    descripcion?: string;
    precioUnitario?: number;
    detalleOrdenId?: number;
    tipoMuestraId?: number;
}

export class ProcedimientoLabsys implements IProcedimientoLabsys {
    constructor(
        public id?: number,
        public descripcion?: string,
        public precioUnitario?: number,
        public detalleOrdenId?: number,
        public tipoMuestraId?: number
    ) {}
}
