import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';

@Component({
    selector: 'jhi-procedimiento-labsys-detail',
    templateUrl: './procedimiento-labsys-detail.component.html'
})
export class ProcedimientoLabsysDetailComponent implements OnInit {
    procedimiento: IProcedimientoLabsys;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ procedimiento }) => {
            this.procedimiento = procedimiento;
        });
    }

    previousState() {
        window.history.back();
    }
}
