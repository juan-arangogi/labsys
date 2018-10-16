/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LabsysTestModule } from '../../../test.module';
import { RecipienteLabsysDeleteDialogComponent } from 'app/entities/recipiente-labsys/recipiente-labsys-delete-dialog.component';
import { RecipienteLabsysService } from 'app/entities/recipiente-labsys/recipiente-labsys.service';

describe('Component Tests', () => {
    describe('RecipienteLabsys Management Delete Component', () => {
        let comp: RecipienteLabsysDeleteDialogComponent;
        let fixture: ComponentFixture<RecipienteLabsysDeleteDialogComponent>;
        let service: RecipienteLabsysService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [RecipienteLabsysDeleteDialogComponent]
            })
                .overrideTemplate(RecipienteLabsysDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecipienteLabsysDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipienteLabsysService);
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
