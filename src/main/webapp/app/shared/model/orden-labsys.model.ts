import { IDetalleOrdenLabsys } from 'app/shared/model//detalle-orden-labsys.model';

export const enum EstadoOrden {
    REGISTRADA = 'REGISTRADA',
    EN_PROCESO = 'EN_PROCESO',
    CANCELADA = 'CANCELADA',
    ANULADA = 'ANULADA',
    FINALIZADA = 'FINALIZADA',
    REMITIDA = 'REMITIDA'
}

export interface IOrdenLabsys {
    id?: number;
    total?: number;
    estado?: EstadoOrden;
    detalleOrdens?: IDetalleOrdenLabsys[];
    pacienteId?: number;
    entidadSaludId?: number;
    medicoId?: number;
}

export class OrdenLabsys implements IOrdenLabsys {
    constructor(
        public id?: number,
        public total?: number,
        public estado?: EstadoOrden,
        public detalleOrdens?: IDetalleOrdenLabsys[],
        public pacienteId?: number,
        public entidadSaludId?: number,
        public medicoId?: number
    ) {}
}
