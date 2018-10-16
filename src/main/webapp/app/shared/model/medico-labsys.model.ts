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

export const enum Especialidad {
    MEDICINA_GENERAL = 'MEDICINA_GENERAL',
    CARDIOLOGIA = 'CARDIOLOGIA',
    PEDIATRIA = 'PEDIATRIA',
    CIRUGIA = 'CIRUGIA',
    REUMATOLOGIA = 'REUMATOLOGIA',
    ORTOPEDIA = 'ORTOPEDIA'
}

export interface IMedicoLabsys {
    id?: number;
    tipoDocumento?: TipoDocumento;
    documento?: string;
    nombres?: string;
    apellidos?: string;
    especialidad?: Especialidad;
    tarjetaProfesional?: string;
    ordens?: IOrdenLabsys[];
    entidadSaludId?: number;
}

export class MedicoLabsys implements IMedicoLabsys {
    constructor(
        public id?: number,
        public tipoDocumento?: TipoDocumento,
        public documento?: string,
        public nombres?: string,
        public apellidos?: string,
        public especialidad?: Especialidad,
        public tarjetaProfesional?: string,
        public ordens?: IOrdenLabsys[],
        public entidadSaludId?: number
    ) {}
}
