import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPacienteLabsys } from 'app/shared/model/paciente-labsys.model';

@Component({
    selector: 'jhi-paciente-labsys-detail',
    templateUrl: './paciente-labsys-detail.component.html'
})
export class PacienteLabsysDetailComponent implements OnInit {
    paciente: IPacienteLabsys;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paciente }) => {
            this.paciente = paciente;
        });
    }

    previousState() {
        window.history.back();
    }
}
