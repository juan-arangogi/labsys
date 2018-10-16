import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';
import { DetalleOrdenLabsysService } from './detalle-orden-labsys.service';

@Component({
    selector: 'jhi-detalle-orden-labsys-delete-dialog',
    templateUrl: './detalle-orden-labsys-delete-dialog.component.html'
})
export class DetalleOrdenLabsysDeleteDialogComponent {
    detalleOrden: IDetalleOrdenLabsys;

    constructor(
        private detalleOrdenService: DetalleOrdenLabsysService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detalleOrdenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'detalleOrdenListModification',
                content: 'Deleted an detalleOrden'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detalle-orden-labsys-delete-popup',
    template: ''
})
export class DetalleOrdenLabsysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detalleOrden }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DetalleOrdenLabsysDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.detalleOrden = detalleOrden;
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
