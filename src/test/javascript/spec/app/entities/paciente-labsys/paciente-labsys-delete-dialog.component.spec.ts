/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LabsysTestModule } from '../../../test.module';
import { PacienteLabsysDeleteDialogComponent } from 'app/entities/paciente-labsys/paciente-labsys-delete-dialog.component';
import { PacienteLabsysService } from 'app/entities/paciente-labsys/paciente-labsys.service';

describe('Component Tests', () => {
    describe('PacienteLabsys Management Delete Component', () => {
        let comp: PacienteLabsysDeleteDialogComponent;
        let fixture: ComponentFixture<PacienteLabsysDeleteDialogComponent>;
        let service: PacienteLabsysService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [PacienteLabsysDeleteDialogComponent]
            })
                .overrideTemplate(PacienteLabsysDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PacienteLabsysDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PacienteLabsysService);
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
