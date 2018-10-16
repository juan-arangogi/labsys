import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPacienteLabsys } from 'app/shared/model/paciente-labsys.model';
import { PacienteLabsysService } from './paciente-labsys.service';

@Component({
    selector: 'jhi-paciente-labsys-delete-dialog',
    templateUrl: './paciente-labsys-delete-dialog.component.html'
})
export class PacienteLabsysDeleteDialogComponent {
    paciente: IPacienteLabsys;

    constructor(
        private pacienteService: PacienteLabsysService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pacienteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pacienteListModification',
                content: 'Deleted an paciente'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-paciente-labsys-delete-popup',
    template: ''
})
export class PacienteLabsysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paciente }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PacienteLabsysDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.paciente = paciente;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
