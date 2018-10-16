import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LabsysRecipienteLabsysModule } from './recipiente-labsys/recipiente-labsys.module';
import { LabsysTipoMuestraLabsysModule } from './tipo-muestra-labsys/tipo-muestra-labsys.module';
import { LabsysProcedimientoLabsysModule } from './procedimiento-labsys/procedimiento-labsys.module';
import { LabsysDetalleOrdenLabsysModule } from './detalle-orden-labsys/detalle-orden-labsys.module';
import { LabsysOrdenLabsysModule } from './orden-labsys/orden-labsys.module';
import { LabsysPacienteLabsysModule } from './paciente-labsys/paciente-labsys.module';
import { LabsysMedicoLabsysModule } from './medico-labsys/medico-labsys.module';
import { LabsysEntidadSaludLabsysModule } from './entidad-salud-labsys/entidad-salud-labsys.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        LabsysRecipienteLabsysModule,
        LabsysTipoMuestraLabsysModule,
        LabsysProcedimientoLabsysModule,
        LabsysDetalleOrdenLabsysModule,
        LabsysOrdenLabsysModule,
        LabsysPacienteLabsysModule,
        LabsysMedicoLabsysModule,
        LabsysEntidadSaludLabsysModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysEntityModule {}
