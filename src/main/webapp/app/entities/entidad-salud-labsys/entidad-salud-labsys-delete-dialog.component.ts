import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';
import { EntidadSaludLabsysService } from './entidad-salud-labsys.service';

@Component({
    selector: 'jhi-entidad-salud-labsys-delete-dialog',
    templateUrl: './entidad-salud-labsys-delete-dialog.component.html'
})
export class EntidadSaludLabsysDeleteDialogComponent {
    entidadSalud: IEntidadSaludLabsys;

    constructor(
        private entidadSaludService: EntidadSaludLabsysService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entidadSaludService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'entidadSaludListModification',
                content: 'Deleted an entidadSalud'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entidad-salud-labsys-delete-popup',
    template: ''
})
export class EntidadSaludLabsysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ entidadSalud }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EntidadSaludLabsysDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.entidadSalud = entidadSalud;
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
