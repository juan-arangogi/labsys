import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicoLabsys } from 'app/shared/model/medico-labsys.model';

@Component({
    selector: 'jhi-medico-labsys-detail',
    templateUrl: './medico-labsys-detail.component.html'
})
export class MedicoLabsysDetailComponent implements OnInit {
    medico: IMedicoLabsys;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ medico }) => {
            this.medico = medico;
        });
    }

    previousState() {
        window.history.back();
    }
}
