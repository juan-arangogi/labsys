import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';

@Component({
    selector: 'jhi-tipo-muestra-labsys-detail',
    templateUrl: './tipo-muestra-labsys-detail.component.html'
})
export class TipoMuestraLabsysDetailComponent implements OnInit {
    tipoMuestra: ITipoMuestraLabsys;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoMuestra }) => {
            this.tipoMuestra = tipoMuestra;
        });
    }

    previousState() {
        window.history.back();
    }
}
