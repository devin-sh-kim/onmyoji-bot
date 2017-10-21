import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatbotMainComponent } from './chatbot-main.component';

describe('ChatbotMainComponent', () => {
  let component: ChatbotMainComponent;
  let fixture: ComponentFixture<ChatbotMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChatbotMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatbotMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
