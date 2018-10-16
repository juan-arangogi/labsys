import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrdenLabsys } from 'app/shared/model/orden-labsys.model';
import { OrdenLabsysService } from './orden-labsys.service';

@Component({
    selector: 'jhi-orden-labsys-delete-dialog',
    templateUrl: './orden-labsys-delete-dialog.component.html'
})
export class OrdenLabsysDeleteDialogComponent {
    orden: IOrdenLabsys;

    constructor(private ordenService: OrdenLabsysService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ordenService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ordenListModification',
                content: 'Deleted an orden'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-orden-labsys-delete-popup',
    template: ''
})
export class OrdenLabsysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orden }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrdenLabsysDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.orden = orden;
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
