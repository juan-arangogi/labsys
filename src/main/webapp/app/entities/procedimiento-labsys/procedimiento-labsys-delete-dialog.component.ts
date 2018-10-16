import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';
import { ProcedimientoLabsysService } from './procedimiento-labsys.service';

@Component({
    selector: 'jhi-procedimiento-labsys-delete-dialog',
    templateUrl: './procedimiento-labsys-delete-dialog.component.html'
})
export class ProcedimientoLabsysDeleteDialogComponent {
    procedimiento: IProcedimientoLabsys;

    constructor(
        private procedimientoService: ProcedimientoLabsysService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.procedimientoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'procedimientoListModification',
                content: 'Deleted an procedimiento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-procedimiento-labsys-delete-popup',
    template: ''
})
export class ProcedimientoLabsysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ procedimiento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProcedimientoLabsysDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.procedimiento = procedimiento;
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
