import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LabsysSharedModule } from 'app/shared';
import {
    TipoMuestraLabsysComponent,
    TipoMuestraLabsysDetailComponent,
    TipoMuestraLabsysUpdateComponent,
    TipoMuestraLabsysDeletePopupComponent,
    TipoMuestraLabsysDeleteDialogComponent,
    tipoMuestraRoute,
    tipoMuestraPopupRoute
} from './';

const ENTITY_STATES = [...tipoMuestraRoute, ...tipoMuestraPopupRoute];

@NgModule({
    imports: [LabsysSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoMuestraLabsysComponent,
        TipoMuestraLabsysDetailComponent,
        TipoMuestraLabsysUpdateComponent,
        TipoMuestraLabsysDeleteDialogComponent,
        TipoMuestraLabsysDeletePopupComponent
    ],
    entryComponents: [
        TipoMuestraLabsysComponent,
        TipoMuestraLabsysUpdateComponent,
        TipoMuestraLabsysDeleteDialogComponent,
        TipoMuestraLabsysDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LabsysTipoMuestraLabsysModule {}
