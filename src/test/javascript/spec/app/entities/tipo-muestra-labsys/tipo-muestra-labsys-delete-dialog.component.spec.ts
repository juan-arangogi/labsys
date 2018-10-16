/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LabsysTestModule } from '../../../test.module';
import { TipoMuestraLabsysDeleteDialogComponent } from 'app/entities/tipo-muestra-labsys/tipo-muestra-labsys-delete-dialog.component';
import { TipoMuestraLabsysService } from 'app/entities/tipo-muestra-labsys/tipo-muestra-labsys.service';

describe('Component Tests', () => {
    describe('TipoMuestraLabsys Management Delete Component', () => {
        let comp: TipoMuestraLabsysDeleteDialogComponent;
        let fixture: ComponentFixture<TipoMuestraLabsysDeleteDialogComponent>;
        let service: TipoMuestraLabsysService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [TipoMuestraLabsysDeleteDialogComponent]
            })
                .overrideTemplate(TipoMuestraLabsysDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoMuestraLabsysDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoMuestraLabsysService);
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
