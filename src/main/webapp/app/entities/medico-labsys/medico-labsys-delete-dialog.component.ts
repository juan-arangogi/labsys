import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMedicoLabsys } from 'app/shared/model/medico-labsys.model';
import { MedicoLabsysService } from './medico-labsys.service';

@Component({
    selector: 'jhi-medico-labsys-delete-dialog',
    templateUrl: './medico-labsys-delete-dialog.component.html'
})
export class MedicoLabsysDeleteDialogComponent {
    medico: IMedicoLabsys;

    constructor(private medicoService: MedicoLabsysService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.medicoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'medicoListModification',
                content: 'Deleted an medico'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-medico-labsys-delete-popup',
    template: ''
})
export class MedicoLabsysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ medico }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MedicoLabsysDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.medico = medico;
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
