export interface IDetalleOrdenLabsys {
    id?: number;
    descuento?: number;
    cantidad?: number;
    total?: number;
    procedimientoId?: number;
    ordenId?: number;
}

export class DetalleOrdenLabsys implements IDetalleOrdenLabsys {
    constructor(
        public id?: number,
        public descuento?: number,
        public cantidad?: number,
        public total?: number,
        public procedimientoId?: number,
        public ordenId?: number
    ) {}
}
