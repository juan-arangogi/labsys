export interface IRecipienteLabsys {
    id?: number;
    descripcion?: string;
    tipoMuestraId?: number;
}

export class RecipienteLabsys implements IRecipienteLabsys {
    constructor(public id?: number, public descripcion?: string, public tipoMuestraId?: number) {}
}
