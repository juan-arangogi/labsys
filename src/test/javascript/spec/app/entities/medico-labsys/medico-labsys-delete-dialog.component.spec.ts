/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LabsysTestModule } from '../../../test.module';
import { MedicoLabsysDeleteDialogComponent } from 'app/entities/medico-labsys/medico-labsys-delete-dialog.component';
import { MedicoLabsysService } from 'app/entities/medico-labsys/medico-labsys.service';

describe('Component Tests', () => {
    describe('MedicoLabsys Management Delete Component', () => {
        let comp: MedicoLabsysDeleteDialogComponent;
        let fixture: ComponentFixture<MedicoLabsysDeleteDialogComponent>;
        let service: MedicoLabsysService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [MedicoLabsysDeleteDialogComponent]
            })
                .overrideTemplate(MedicoLabsysDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MedicoLabsysDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedicoLabsysService);
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
