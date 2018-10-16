import { Moment } from 'moment';
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

export const enum Genero {
    MASCULINO = 'MASCULINO',
    FEMENINO = 'FEMENINO'
}

export interface IPacienteLabsys {
    id?: number;
    tipoDocumento?: TipoDocumento;
    documento?: string;
    nombres?: string;
    apellidos?: string;
    genero?: Genero;
    fechaNacimiento?: Moment;
    telefono?: string;
    celular?: string;
    email?: string;
    direccion?: string;
    ciudad?: string;
    ordens?: IOrdenLabsys[];
}

export class PacienteLabsys implements IPacienteLabsys {
    constructor(
        public id?: number,
        public tipoDocumento?: TipoDocumento,
        public documento?: string,
        public nombres?: string,
        public apellidos?: string,
        public genero?: Genero,
        public fechaNacimiento?: Moment,
        public telefono?: string,
        public celular?: string,
        public email?: string,
        public direccion?: string,
        public ciudad?: string,
        public ordens?: IOrdenLabsys[]
    ) {}
}
