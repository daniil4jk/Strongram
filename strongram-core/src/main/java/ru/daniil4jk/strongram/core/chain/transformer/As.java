package ru.daniil4jk.strongram.core.chain.transformer;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostAdded;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostRemoved;
import org.telegram.telegrambots.meta.api.objects.boost.ChatBoostUpdated;
import org.telegram.telegrambots.meta.api.objects.business.BusinessConnection;
import org.telegram.telegrambots.meta.api.objects.business.BusinessMessagesDeleted;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.chat.background.ChatBackground;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.forum.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.games.Game;
import org.telegram.telegrambots.meta.api.objects.gifts.GiftInfo;
import org.telegram.telegrambots.meta.api.objects.gifts.UniqueGiftInfo;
import org.telegram.telegrambots.meta.api.objects.giveaway.Giveaway;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayCompleted;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayCreated;
import org.telegram.telegrambots.meta.api.objects.giveaway.GiveawayWinners;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.location.Location;
import org.telegram.telegrambots.meta.api.objects.message.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.message.PaidMessagePriceChanged;
import org.telegram.telegrambots.meta.api.objects.messageorigin.MessageOrigin;
import org.telegram.telegrambots.meta.api.objects.passport.PassportData;
import org.telegram.telegrambots.meta.api.objects.payments.*;
import org.telegram.telegrambots.meta.api.objects.payments.paidmedia.PaidMediaInfo;
import org.telegram.telegrambots.meta.api.objects.photo.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionCountUpdated;
import org.telegram.telegrambots.meta.api.objects.reactions.MessageReactionUpdated;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.stories.Story;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatEnded;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatParticipantsInvited;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatScheduled;
import org.telegram.telegrambots.meta.api.objects.videochat.VideoChatStarted;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppData;
import ru.daniil4jk.strongram.core.parser.text.TextParserService;

import java.util.List;

public class As {
    public static Transformer<Update> update() {
        return update -> update;
    }

    public static Transformer<String> text() {
        return update -> TextParserService.getInstance().parse(update);
    }

    public static @NotNull Transformer<Message> message() {
        return Update::getMessage;
    }

    public static @NotNull Transformer<String> messageText() {
        return message().andThen(message -> {
            if (message.hasText()) return message.getText();
            if (message.hasCaption()) return message.getCaption();
            return null;
        });
    }

    public static @NotNull Transformer<Integer> date() {
        return message().andThen(Message::getDate);
    }

    public static @NotNull Transformer<User> forwardFrom() {
        return message().andThen(Message::getForwardFrom);
    }

    public static @NotNull Transformer<Chat> forwardFromChat() {
        return message().andThen(Message::getForwardFromChat);
    }

    public static @NotNull Transformer<Integer> forwardDate() {
        return message().andThen(Message::getForwardDate);
    }

    public static @NotNull Transformer<Contact> contact() {
        return message().andThen(Message::getContact);
    }

    public static @NotNull Transformer<Location> location() {
        return message().andThen(Message::getLocation);
    }

    public static @NotNull Transformer<Venue> venue() {
        return message().andThen(Message::getVenue);
    }

    public static @NotNull Transformer<Animation> animation() {
        return message().andThen(Message::getAnimation);
    }

    public static @NotNull Transformer<MaybeInaccessibleMessage> pinnedMessage() {
        return message().andThen(Message::getPinnedMessage);
    }

    public static @NotNull Transformer<User> leftChatMember() {
        return message().andThen(Message::getLeftChatMember);
    }

    public static @NotNull Transformer<String> newChatTitle() {
        return message().andThen(Message::getNewChatTitle);
    }

    public static @NotNull Transformer<List<PhotoSize>> newChatPhoto() {
        return message().andThen(Message::getNewChatPhoto);
    }

    public static @NotNull Transformer<Boolean> deleteChatPhoto() {
        return message().andThen(Message::getDeleteChatPhoto);
    }

    public static @NotNull Transformer<Boolean> groupchatCreated() {
        return message().andThen(Message::getGroupchatCreated);
    }

    public static @NotNull Transformer<Message> replyToMessage() {
        return message().andThen(Message::getReplyToMessage);
    }

    public static @NotNull Transformer<Voice> voice() {
        return message().andThen(Message::getVoice);
    }

    public static @NotNull Transformer<String> caption() {
        return message().andThen(Message::getCaption);
    }

    public static @NotNull Transformer<Boolean> superGroupCreated() {
        return message().andThen(Message::getSuperGroupCreated);
    }

    public static @NotNull Transformer<Boolean> channelChatCreated() {
        return message().andThen(Message::getChannelChatCreated);
    }

    public static @NotNull Transformer<Long> migrateToChatId() {
        return message().andThen(Message::getMigrateToChatId);
    }

    public static @NotNull Transformer<Long> migrateFromChatId() {
        return message().andThen(Message::getMigrateFromChatId);
    }

    public static @NotNull Transformer<Integer> editDate() {
        return message().andThen(Message::getEditDate);
    }

    public static @NotNull Transformer<Game> game() {
        return message().andThen(Message::getGame);
    }

    public static @NotNull Transformer<Integer> forwardFromMessageId() {
        return message().andThen(Message::getForwardFromMessageId);
    }

    public static @NotNull Transformer<Invoice> invoice() {
        return message().andThen(Message::getInvoice);
    }

    public static @NotNull Transformer<SuccessfulPayment> successfulPayment() {
        return message().andThen(Message::getSuccessfulPayment);
    }

    public static @NotNull Transformer<VideoNote> videoNote() {
        return message().andThen(Message::getVideoNote);
    }

    public static @NotNull Transformer<String> authorSignature() {
        return message().andThen(Message::getAuthorSignature);
    }

    public static @NotNull Transformer<String> forwardSignature() {
        return message().andThen(Message::getForwardSignature);
    }

    public static @NotNull Transformer<String> mediaGroupId() {
        return message().andThen(Message::getMediaGroupId);
    }

    public static @NotNull Transformer<String> connectedWebsite() {
        return message().andThen(Message::getConnectedWebsite);
    }

    public static @NotNull Transformer<PassportData> passportData() {
        return message().andThen(Message::getPassportData);
    }

    public static @NotNull Transformer<String> forwardSenderName() {
        return message().andThen(Message::getForwardSenderName);
    }

    public static @NotNull Transformer<Poll> messagePoll() {
        return message().andThen(Message::getPoll);
    }

    public static @NotNull Transformer<InlineKeyboardMarkup> replyMarkup() {
        return message().andThen(Message::getReplyMarkup);
    }

    public static @NotNull Transformer<Dice> dice() {
        return message().andThen(Message::getDice);
    }

    public static @NotNull Transformer<User> viaBot() {
        return message().andThen(Message::getViaBot);
    }

    public static @NotNull Transformer<Chat> senderChat() {
        return message().andThen(Message::getSenderChat);
    }

    public static @NotNull Transformer<ProximityAlertTriggered> proximityAlertTriggered() {
        return message().andThen(Message::getProximityAlertTriggered);
    }

    public static @NotNull Transformer<MessageAutoDeleteTimerChanged> messageAutoDeleteTimerChanged() {
        return message().andThen(Message::getMessageAutoDeleteTimerChanged);
    }

    public static @NotNull Transformer<Boolean> isAutomaticForward() {
        return message().andThen(Message::getIsAutomaticForward);
    }

    public static @NotNull Transformer<Boolean> hasProtectedContent() {
        return message().andThen(Message::getHasProtectedContent);
    }

    public static @NotNull Transformer<WebAppData> webAppData() {
        return message().andThen(Message::getWebAppData);
    }

    public static @NotNull Transformer<VideoChatStarted> videoChatStarted() {
        return message().andThen(Message::getVideoChatStarted);
    }

    public static @NotNull Transformer<VideoChatEnded> videoChatEnded() {
        return message().andThen(Message::getVideoChatEnded);
    }

    public static @NotNull Transformer<VideoChatParticipantsInvited> videoChatParticipantsInvited() {
        return message().andThen(Message::getVideoChatParticipantsInvited);
    }

    public static @NotNull Transformer<VideoChatScheduled> videoChatScheduled() {
        return message().andThen(Message::getVideoChatScheduled);
    }

    public static @NotNull Transformer<Boolean> isTopicMessage() {
        return message().andThen(Message::getIsTopicMessage);
    }

    public static @NotNull Transformer<ForumTopicCreated> forumTopicCreated() {
        return message().andThen(Message::getForumTopicCreated);
    }

    public static @NotNull Transformer<ForumTopicClosed> forumTopicClosed() {
        return message().andThen(Message::getForumTopicClosed);
    }

    public static @NotNull Transformer<ForumTopicReopened> forumTopicReopened() {
        return message().andThen(Message::getForumTopicReopened);
    }

    public static @NotNull Transformer<ForumTopicEdited> forumTopicEdited() {
        return message().andThen(Message::getForumTopicEdited);
    }

    public static @NotNull Transformer<GeneralForumTopicHidden> generalForumTopicHidden() {
        return message().andThen(Message::getGeneralForumTopicHidden);
    }

    public static @NotNull Transformer<GeneralForumTopicUnhidden> generalForumTopicUnhidden() {
        return message().andThen(Message::getGeneralForumTopicUnhidden);
    }

    public static @NotNull Transformer<WriteAccessAllowed> writeAccessAllowed() {
        return message().andThen(Message::getWriteAccessAllowed);
    }

    public static @NotNull Transformer<Boolean> hasMediaSpoiler() {
        return message().andThen(Message::getHasMediaSpoiler);
    }

    public static @NotNull Transformer<UserShared> userShared() {
        return message().andThen(Message::getUserShared);
    }

    public static @NotNull Transformer<ChatShared> chatShared() {
        return message().andThen(Message::getChatShared);
    }

    public static @NotNull Transformer<Story> story() {
        return message().andThen(Message::getStory);
    }

    public static @NotNull Transformer<ExternalReplyInfo> externalReplyInfo() {
        return message().andThen(Message::getExternalReplyInfo);
    }

    public static @NotNull Transformer<MessageOrigin> forwardOrigin() {
        return message().andThen(Message::getForwardOrigin);
    }

    public static @NotNull Transformer<LinkPreviewOptions> linkPreviewOptions() {
        return message().andThen(Message::getLinkPreviewOptions);
    }

    public static @NotNull Transformer<TextQuote> quote() {
        return message().andThen(Message::getQuote);
    }

    public static @NotNull Transformer<UsersShared> usersShared() {
        return message().andThen(Message::getUsersShared);
    }

    public static @NotNull Transformer<GiveawayCreated> giveawayCreated() {
        return message().andThen(Message::getGiveawayCreated);
    }

    public static @NotNull Transformer<Giveaway> giveaway() {
        return message().andThen(Message::getGiveaway);
    }

    public static @NotNull Transformer<GiveawayWinners> giveawayWinners() {
        return message().andThen(Message::getGiveawayWinners);
    }

    public static @NotNull Transformer<GiveawayCompleted> giveawayCompleted() {
        return message().andThen(Message::getGiveawayCompleted);
    }

    public static @NotNull Transformer<Story> replyToStory() {
        return message().andThen(Message::getReplyToStory);
    }

    public static @NotNull Transformer<ChatBoostAdded> boostAdded() {
        return message().andThen(Message::getBoostAdded);
    }

    public static @NotNull Transformer<Integer> senderBoostCount() {
        return message().andThen(Message::getSenderBoostCount);
    }

    public static @NotNull Transformer<String> businessConnectionId() {
        return message().andThen(Message::getBusinessConnectionId);
    }

    public static @NotNull Transformer<User> senderBusinessBot() {
        return message().andThen(Message::getSenderBusinessBot);
    }

    public static @NotNull Transformer<Boolean> isFromOffline() {
        return message().andThen(Message::getIsFromOffline);
    }

    public static @NotNull Transformer<ChatBackground> chatBackgroundSet() {
        return message().andThen(Message::getChatBackgroundSet);
    }

    public static @NotNull Transformer<String> effectId() {
        return message().andThen(Message::getEffectId);
    }

    public static @NotNull Transformer<Boolean> showCaptionAboveMedia() {
        return message().andThen(Message::getShowCaptionAboveMedia);
    }

    public static @NotNull Transformer<PaidMediaInfo> paidMedia() {
        return message().andThen(Message::getPaidMedia);
    }

    public static @NotNull Transformer<RefundedPayment> refundedPayment() {
        return message().andThen(Message::getRefundedPayment);
    }

    public static @NotNull Transformer<GiftInfo> gift() {
        return message().andThen(Message::getGift);
    }

    public static @NotNull Transformer<UniqueGiftInfo> uniqueGift() {
        return message().andThen(Message::getUniqueGift);
    }

    public static @NotNull Transformer<PaidMessagePriceChanged> paidMessagePriceChanged() {
        return message().andThen(Message::getPaidMessagePriceChanged);
    }

    public static @NotNull Transformer<Integer> paidStarCount() {
        return message().andThen(Message::getPaidStarCount);
    }

    public static @NotNull Transformer<InlineQuery> inlineQuery() {
        return Update::getInlineQuery;
    }

    public static @NotNull Transformer<ChosenInlineQuery> chosenInlineQuery() {
        return Update::getChosenInlineQuery;
    }

    public static @NotNull Transformer<CallbackQuery> callbackQuery() {
        return Update::getCallbackQuery;
    }

    public static @NotNull Transformer<Message> editedMessage() {
        return Update::getEditedMessage;
    }

    public static @NotNull Transformer<Message> channelPost() {
        return Update::getChannelPost;
    }

    public static @NotNull Transformer<Message> editedChannelPost() {
        return Update::getEditedChannelPost;
    }

    public static @NotNull Transformer<ShippingQuery> shippingQuery() {
        return Update::getShippingQuery;
    }

    public static @NotNull Transformer<PreCheckoutQuery> preCheckoutQuery() {
        return Update::getPreCheckoutQuery;
    }

    public static @NotNull Transformer<Poll> poll() {
        return Update::getPoll;
    }

    public static @NotNull Transformer<PollAnswer> pollAnswer() {
        return Update::getPollAnswer;
    }

    public static @NotNull Transformer<ChatMemberUpdated> myChatMember() {
        return Update::getMyChatMember;
    }

    public static @NotNull Transformer<ChatMemberUpdated> chatMember() {
        return Update::getChatMember;
    }

    public static @NotNull Transformer<ChatJoinRequest> chatJoinRequest() {
        return Update::getChatJoinRequest;
    }

    public static @NotNull Transformer<MessageReactionUpdated> messageReaction() {
        return Update::getMessageReaction;
    }

    public static @NotNull Transformer<MessageReactionCountUpdated> messageReactionCount() {
        return Update::getMessageReactionCount;
    }

    public static @NotNull Transformer<ChatBoostUpdated> chatBoost() {
        return Update::getChatBoost;
    }

    public static @NotNull Transformer<ChatBoostRemoved> removedChatBoost() {
        return Update::getRemovedChatBoost;
    }

    public static @NotNull Transformer<BusinessConnection> businessConnection() {
        return Update::getBusinessConnection;
    }

    public static @NotNull Transformer<Message> businessMessage() {
        return Update::getBusinessMessage;
    }

    public static @NotNull Transformer<Message> editedBusinessMessage() {
        return Update::getEditedBuinessMessage;
    }

    public static @NotNull Transformer<BusinessMessagesDeleted> deletedBusinessMessages() {
        return Update::getDeletedBusinessMessages;
    }

    public static @NotNull Transformer<PaidMediaPurchased> paidMediaPurchased() {
        return Update::getPaidMediaPurchased;
    }
}
