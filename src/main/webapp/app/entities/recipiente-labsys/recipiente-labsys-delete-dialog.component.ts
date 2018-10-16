import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';
import { RecipienteLabsysService } from './recipiente-labsys.service';

@Component({
    selector: 'jhi-recipiente-labsys-delete-dialog',
    templateUrl: './recipiente-labsys-delete-dialog.component.html'
})
export class RecipienteLabsysDeleteDialogComponent {
    recipiente: IRecipienteLabsys;

    constructor(
        private recipienteService: RecipienteLabsysService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recipienteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recipienteListModification',
                content: 'Deleted an recipiente'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recipiente-labsys-delete-popup',
    template: ''
})
export class RecipienteLabsysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipiente }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecipienteLabsysDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.recipiente = recipiente;
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
