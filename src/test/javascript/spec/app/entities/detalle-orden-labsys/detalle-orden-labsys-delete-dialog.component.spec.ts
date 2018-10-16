/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LabsysTestModule } from '../../../test.module';
import { DetalleOrdenLabsysDeleteDialogComponent } from 'app/entities/detalle-orden-labsys/detalle-orden-labsys-delete-dialog.component';
import { DetalleOrdenLabsysService } from 'app/entities/detalle-orden-labsys/detalle-orden-labsys.service';

describe('Component Tests', () => {
    describe('DetalleOrdenLabsys Management Delete Component', () => {
        let comp: DetalleOrdenLabsysDeleteDialogComponent;
        let fixture: ComponentFixture<DetalleOrdenLabsysDeleteDialogComponent>;
        let service: DetalleOrdenLabsysService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [DetalleOrdenLabsysDeleteDialogComponent]
            })
                .overrideTemplate(DetalleOrdenLabsysDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DetalleOrdenLabsysDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleOrdenLabsysService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
