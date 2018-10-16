import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';

@Component({
    selector: 'jhi-entidad-salud-labsys-detail',
    templateUrl: './entidad-salud-labsys-detail.component.html'
})
export class EntidadSaludLabsysDetailComponent implements OnInit {
    entidadSalud: IEntidadSaludLabsys;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entidadSalud }) => {
            this.entidadSalud = entidadSalud;
        });
    }

    previousState() {
        window.history.back();
    }
}
