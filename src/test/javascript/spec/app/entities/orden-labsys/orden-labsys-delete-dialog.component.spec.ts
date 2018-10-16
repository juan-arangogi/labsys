/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LabsysTestModule } from '../../../test.module';
import { OrdenLabsysDeleteDialogComponent } from 'app/entities/orden-labsys/orden-labsys-delete-dialog.component';
import { OrdenLabsysService } from 'app/entities/orden-labsys/orden-labsys.service';

describe('Component Tests', () => {
    describe('OrdenLabsys Management Delete Component', () => {
        let comp: OrdenLabsysDeleteDialogComponent;
        let fixture: ComponentFixture<OrdenLabsysDeleteDialogComponent>;
        let service: OrdenLabsysService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [OrdenLabsysDeleteDialogComponent]
            })
                .overrideTemplate(OrdenLabsysDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrdenLabsysDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdenLabsysService);
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
