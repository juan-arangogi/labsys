import { IMedicoLabsys } from 'app/shared/model//medico-labsys.model';
import { IOrdenLabsys } from 'app/shared/model//orden-labsys.model';

export const enum TipoDocumento {
    ASI = 'ASI',
    MSI = 'MSI',
    CC = 'CC',
    CE = 'CE',
    RC = 'RC',
    TI = 'TI',
    CD = 'CD'
}

export interface IEntidadSaludLabsys {
    id?: number;
    tipoDocumento?: TipoDocumento;
    documento?: string;
    razonSocial?: string;
    direccion?: string;
    telefono?: string;
    medicos?: IMedicoLabsys[];
    ordens?: IOrdenLabsys[];
}

export class EntidadSaludLabsys implements IEntidadSaludLabsys {
    constructor(
        public id?: number,
        public tipoDocumento?: TipoDocumento,
        public documento?: string,
        public razonSocial?: string,
        public direccion?: string,
        public telefono?: string,
        public medicos?: IMedicoLabsys[],
        public ordens?: IOrdenLabsys[]
    ) {}
}
