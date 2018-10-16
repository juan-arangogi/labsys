import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';
import { TipoMuestraLabsysService } from './tipo-muestra-labsys.service';

@Component({
    selector: 'jhi-tipo-muestra-labsys-delete-dialog',
    templateUrl: './tipo-muestra-labsys-delete-dialog.component.html'
})
export class TipoMuestraLabsysDeleteDialogComponent {
    tipoMuestra: ITipoMuestraLabsys;

    constructor(
        private tipoMuestraService: TipoMuestraLabsysService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoMuestraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoMuestraListModification',
                content: 'Deleted an tipoMuestra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-muestra-labsys-delete-popup',
    template: ''
})
export class TipoMuestraLabsysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoMuestra }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoMuestraLabsysDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tipoMuestra = tipoMuestra;
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
